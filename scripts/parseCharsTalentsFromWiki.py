from bs4 import BeautifulSoup
import json
import sys
from tqdm import tqdm
import pandas as pd
import requests as req
import re

chars_url_path = "../src/main/resources/chars_url.json"
chars_talents_path = "../src/main/resources/chars_talents.json"


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

    chars = []
    with open(chars_url_path, "r") as urls:
        chars_url = json.load(urls)

        for i in tqdm(range(3, len(chars_url['urls']))):
            resp = req.get(chars_url['urls'][i])

            soup = BeautifulSoup(resp.text, 'lxml')

            # Stats

            table_el = soup.find_all(string=re.compile('1-Hit DMG'))
            table = table_el[0].parent.parent.parent


            # Delete useless rows
            #for tag in table.find_all(string=re.compile("Ascension Cost")):
            #    tag.parent.parent.parent.decompose()

            df = pd.DataFrame(table_to_2d(table))



            if (chars_url['urls'][i].find('Traveler', 36) != -1):
                df.iat[7, 0] = df.iat[7, 0].replace(' Attack DMG (Aether) (%)', '')
                df.drop(index=[10], inplace=True)


            df.drop(index=[0], inplace=True)

            #df.drop(index=[0, 6, 8, 9], inplace=True)

            for r in range(len(df)):
                df.iat[r, 0] = df.iat[r, 0].replace(' DMG (%)', '')
                df.iat[r, 0] = df.iat[r, 0].replace('-Hit', '')


            print()
            print()
            print(df)
            print()

            exit(0)


            # Convert data to int/double
            df.iloc[:, 0] = pd.to_numeric(df.iloc[:, 0])
            df.iloc[:, 1] = pd.to_numeric(df.iloc[:, 1])
            df.iloc[:, 2] = pd.to_numeric(df.iloc[:, 2])
            df.iloc[:, 3] = pd.to_numeric(df.iloc[:, 3])


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
        "lvl": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        "chars": chars
    }

    with open(chars_talents_path, "w") as chars:
        json.dump(res, chars, indent=4)
