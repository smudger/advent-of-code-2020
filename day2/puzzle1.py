#! /usr/bin/env python3

import common, re

def countMatches(needle, haystack):
    return len(re.findall(needle, haystack))

def matchesBetween(min, max, needle, haystack):
    return min <= countMatches(needle, haystack) <= max

print(common.matchingPredicate(common.get_passwords(), matchesBetween))
