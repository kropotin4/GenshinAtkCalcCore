from bs4 import BeautifulSoup
import json
import sys
from tqdm import tqdm
import pandas as pd
import requests as req
import re

weapons_url_path = "../src/main/resources/weapons_url.json"
weapons_list_path = "../src/main/resources/weapons.json"


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


def check_stars(stars_tag):
    for tag in stars_tag:
        for i in [3, 4, 5]:
            if re.search(str(i) + ' star', str(tag), re.IGNORECASE):
                return i

    return 0

if __name__ == '__main__':

    weapon_types = ["Sword", "Claymore", "Polearm", "Catalyst", "Bow"]
    weapons = []
    with open(weapons_url_path, "r") as urls:
        weapons_url = json.load(urls)

        for i in tqdm(range(len(weapons_url['urls']))):
            resp = req.get(weapons_url['urls'][i])

            soup = BeautifulSoup(resp.text, 'lxml')

            # Weapon type
            weapon_type_tag = soup.find_all(string=re.compile('Weapon Type'))
            weapon_type = "?"
            for tag in weapon_type_tag[0].parent.parent.children:
                if tag.name is not None:
                    tag_s = tag.text.strip()

                    try:
                        index_value = weapon_types.index(tag_s)
                    except ValueError:
                        index_value = -1

                    if index_value is not -1:
                        weapon_type = tag_s
                        break

            # Stars
            stars = check_stars(weapon_type_tag[0].parent.parent.parent.children)

            # Stats

            table_el = soup.find_all(string=re.compile('2nd Stat'))
            table = table_el[2].parent.parent.parent.parent

            # Delete useless rows
            for tag in table.find_all(string=re.compile("Ascension Cost")):
                tag.parent.parent.parent.decompose()

            df = pd.DataFrame(table_to_2d(table))

            # Sub Stat
            subStat = df.iat[0, 3].replace("2nd Stat", "")
            subStat = subStat.replace("(", "")
            subStat = subStat.replace(")", "")
            subStat = subStat.replace("\n", "")

            # Delete Ascension Phase and Level cols
            df.drop(columns=[0, 1], inplace=True)
            df.drop(index=[0], inplace=True)

            for r in range(len(df)):
                for c in range(len(df.columns)):
                    df.iat[r, c] = df.iat[r, c].replace("\n", "")
                    df.iat[r, c] = df.iat[r, c].replace("%", "")

            # Convert data to int/double
            df.iloc[:, 0] = pd.to_numeric(df.iloc[:, 0])
            df.iloc[:, 1] = pd.to_numeric(df.iloc[:, 1])

            # Name
            name = re.match(
                    r"^[a-zA-Z\"\s]{3,}",
                    soup.find_all("title")[0].text
                ).group(0).strip()

            char = {
                "name": name,
                "type": weapon_type,
                'stars': stars,
                "baseATK": df.iloc[:, 0].tolist(),
                "subStatType": subStat,
                "subStat": df.iloc[:, 1].tolist()
            }

            weapons.append(char)
            #print(name + ": ok")

    # Result
    res = {
        "lvl": [1, 20, 21, 40, 41, 50, 51, 60, 61, 70, 71, 80, 81, 90],
        "weapons": weapons
    }

    with open(weapons_list_path, "w") as weapons:
        json.dump(res, weapons, indent=4)
