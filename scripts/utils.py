

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
