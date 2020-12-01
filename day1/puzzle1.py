#! /usr/bin/env python3

import csv

with open('expense_report.csv', newline='') as csvFile:
    expenseReport = [int(row[0]) for row in csv.reader(csvFile)]
    for row in expenseReport:
        hasPair = [i for i in expenseReport if i == (2020 - row)]
        if hasPair:
            print(row * (2020 - row))
            break
