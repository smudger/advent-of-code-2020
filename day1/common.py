#! /usr/bin/env python3

import csv

def multiply_pair_summing_to(desired_sum):
    with open('expense_report.csv', newline='') as csv_file:
        expense_report = [int(row[0]) for row in csv.reader(csv_file)]

        def find_match(row):
            return [(i, row) for i in expense_report if i == (desired_sum - row)]

        def parse_pair(pair):
            return (pair[0][0], pair[0][1])

        pairs = (find_match(row) for row in expense_report)
        matching_pair = [parse_pair(pair) for pair in pairs if pair]

        if matching_pair:
            return matching_pair[0][0] * matching_pair[0][1]

        return False
