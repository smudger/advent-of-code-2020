#! /usr/bin/env python3

import csv

def parseRow(row):
    return (int(row[0].split('-')[0]), int(row[0].split('-')[1]), row[1].rstrip(':'), row[2])

def get_passwords():
    with open('passwords.csv', newline='') as csv_file:
        return [parseRow(row) for row in csv.reader(csv_file, delimiter=' ')]

def matchingPredicate(list, predicate):
    return len([item for item in list if predicate(item[0], item[1], item[2], item[3])])
