#! /usr/bin/env python3

import csv

with open('expense_report.csv', newline='') as csvFile:
    expenseReport = [int(row[0]) for row in csv.reader(csvFile)]

    def find_match(row):
        return [(i, row) for i in expenseReport if i == (2020 - row)]

    def parse_pair(pair):
        return (pair[0][0], pair[0][1])

    pairs = (find_match(row) for row in expenseReport)
    matchingPair = [parse_pair(pair) for pair in pairs if pair][0]

    print(matchingPair[0] * matchingPair[1])
