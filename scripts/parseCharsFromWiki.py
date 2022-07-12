from bs4 import BeautifulSoup
import json
import sys
from tqdm import tqdm
import pandas as pd
import requests as req
import re

chars_url_path = "../src/main/resources/chars_url.json"
chars_list_path = "../src/main/resources/chars.json"


def table_to_2d(table_tag):
    rows = table_tag("tr")
    cols = rows[0](["td", "th"])
    table = [[None] * len(cols) for _ in range(len(rows))]
    for row_i, row in enumerate(rows):
        for col_i, col in enumerate(row(["td", "th"])):
            insert(table, row_i, col_i, col)
    return table


def insert(table, row, col, element):
    if row >= len(table) or col >= len(table[row]):
        return
    if table[row][col] is None:
        value = element.get_text()
        table[row][col] = value
        if element.has_attr("colspan"):
            span = int(element["colspan"])
            for i in range(1, span):
                table[row][col+i] = value
        if element.has_attr("rowspan"):
            span = int(element["rowspan"])
            for i in range(1, span):
                table[row+i][col] = value
    else:
        insert(table, row, col + 1, element)


if __name__ == '__main__':

    elements = ["Adaptive", "Anemo", "Cryo", "Electro", "Geo", "Hydro", "Pyro"]
    chars = []
    with open(chars_url_path, "r") as urls:
        chars_url = json.load(urls)

        for i in tqdm(range(len(chars_url['urls']))):
            resp = req.get(chars_url['urls'][i])

            soup = BeautifulSoup(resp.text, 'lxml')

            # Element
            elem = "?"
            for tag in soup.find_all("td", {"data-source": "element"}):
                text = tag.text.strip()

                try:
                    index_value = elements.index(text)
                except ValueError:
                    index_value = -1

                if index_value is not -1:
                    elem = text
                    break

            # Stats

            table_el = soup.find_all(string=re.compile('Special Stat'))
            table = table_el[0].parent.parent.parent.parent

            # Delete useless rows
            for tag in table.find_all(string=re.compile("Ascension Cost")):
                tag.parent.parent.parent.decompose()

            df = pd.DataFrame(table_to_2d(table))

            # Sub Stat
            subStat = df.iat[0, 5].replace("Special Stat2", "")
            subStat = subStat.replace("(", "")
            subStat = subStat.replace(")", "")
            subStat = subStat.replace("\n", "")

            # Delete Ascension Phase and Level cols
            df.drop(columns=[0, 1], inplace=True)
            df.drop(index=[0], inplace=True)

            for r in range(len(df)):
                df.iat[r, 0] = df.iat[r, 0].replace(",", "")
                df.iat[r, 3] = df.iat[r, 3].replace("—", "0")
                for c in range(len(df.columns)):
                    df.iat[r, c] = df.iat[r, c].replace("\n", "")
                    df.iat[r, c] = df.iat[r, c].replace("%", "")

            # Convert data to int/double
            df.iloc[:, 0] = pd.to_numeric(df.iloc[:, 0])
            df.iloc[:, 1] = pd.to_numeric(df.iloc[:, 1])
            df.iloc[:, 2] = pd.to_numeric(df.iloc[:, 2])
            df.iloc[:, 3] = pd.to_numeric(df.iloc[:, 3])

            # Name
            name = re.match(
                    r"^[a-zA-Z\s]{3,}",
                    soup.find_all("title")[0].text
                ).group(0).strip()

            char = {
                "name": name,
                "element": elem,
                "baseHP": df.iloc[:, 0].tolist(),
                "baseATK": df.iloc[:, 1].tolist(),
                "baseDEF": df.iloc[:, 2].tolist(),
                "subStatType": subStat,
                "subStat": df.iloc[:, 3].tolist()
            }

            chars.append(char)

    # Result
    res = {
        "lvl": [1, 20, 21, 40, 41, 50, 51, 60, 61, 70, 71, 80, 81, 90],
        "chars": chars
    }

    with open(chars_list_path, "w") as chars:
        json.dump(res, chars, indent=4)
