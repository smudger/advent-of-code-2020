#! /usr/bin/env python3

import csv
import common

with open('expense_report.csv', newline='') as csvFile:
    expenseReport = [int(row[0]) for row in csv.reader(csvFile)]

    for row in expenseReport:
        match = common.multiply_pair_summing_to(2020 - row)
        if match:
            print(match * row)
            break
