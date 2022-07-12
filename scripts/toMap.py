import sys
import re


if __name__ == '__main__':

    for arg in sys.argv:
        if arg == "--help" or arg == "-h":
            print("toMap.py [-h, --help] [arg1]")
            print("Convert table format of Weapon stats to java HashMap")
            print("where:")
            print("\t-h\t -- print help")
            print("\t  --help")
            print("\targ1\t -- file with table")
            sys.exit()

    file = open(sys.argv[1], "r")
    table = ''.join([line for line in file])
    file.close()

    table = re.sub("\s{2,}" , " ", table)
    table = table.replace("%", "")
    table_rows = table.split('\n')

    map_rows = []
    row_names = []
    lvl = [1, 20, 40, 50, 60, 70, 80, 90]
    for row in table_rows:
        r = row.split(" ")
        row_names.append(r[0].replace(".", ""))
        map_str = "typeSS" + row_names[len(row_names) - 1] + " = new HashMap<Integer, Double>() {{\n"
        for i in range(len(lvl)):
            map_str += "    put(" + str(lvl[i]) + ", " + r[i] + ");\n"

        map_str += "}};"
        map_rows.append(map_str)

    file_res = open("res.txt", "w")
    res = ""

    for i in range(len(row_names)):
        res += "HashMap<Integer, Double> typeSS" + row_names[i] + ";\n"

    res += '\n'
    for r in map_rows:
        res += r + "\n"

    file_res.write(res)
    file_res.close()
