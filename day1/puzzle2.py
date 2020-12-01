#! /usr/bin/env python3

import common

expense_report = common.get_expense_report()

pairs = [(row, common.multiply_pair_summing_to(2020 - row, expense_report)) for row in expense_report]
matching_pair = [pair for pair in pairs if pair[1]]
print(matching_pair[0][0] * matching_pair[0][1])
