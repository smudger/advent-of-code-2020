#! /usr/bin/env python3

import common, re

def hasCharInPos(first, second, char, haystack):
    return char in (haystack[first - 1], haystack[second - 1]) and not haystack[first - 1] == haystack[second - 1]

print(common.matchingPredicate(common.get_passwords(), hasCharInPos))
