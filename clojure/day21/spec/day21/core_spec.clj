(ns day21.core-spec
  (:require [speclj.core :refer :all]
            [day21.core :refer :all]))

(describe "The menu parser"
  (it "should format input into a vector of ingredients and allergens"
    (should= [
      {:ingredients ["abc" "def"], :allergens ["fish" "egg"]}
      {:ingredients ["ghi" "jkl"], :allergens ["nuts" "sesame"]}
    ] (parse-menu "abc def (contains fish, egg)\nghi jkl (contains nuts, sesame)"))
  )
)

