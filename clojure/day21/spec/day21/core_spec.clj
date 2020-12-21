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

(describe "The allergens getter"
  (it "should return a unique set of allergens from the menu"
    (should= #{"fish" "egg" "nuts" "sesame"} (get-allergens [
      {:ingredients ["abc" "def"], :allergens ["fish" "egg" "nuts"]}
      {:ingredients ["ghi" "jkl"], :allergens ["nuts" "sesame" "egg"]}
      {:ingredients ["noj" "awdwa"], :allergens ["sesame" "sesame" "egg"]}
      {:ingredients ["ghi" "adwa"], :allergens ["sesame" "nuts" "nuts"]}
    ]))
  )
)

(describe "The possible ingredient finder"
  (it "should return a set of the ingredients always present with the allergen"
    (should= #{"abc" "def"} (get-possible-ingredients [
      {:ingredients ["abc" "def" "ghi"], :allergens ["fish" "egg" "nuts"]}
      {:ingredients ["ghi" "def" "abc"], :allergens ["nuts" "sesame" "egg"]}
      {:ingredients ["ghi" "bdh" "def"], :allergens ["sesame" "nuts"]}
      {:ingredients ["def" "abc" "bdh" "dwa"], :allergens ["sesame" "egg" "fish"]}
    ] "egg"))
  )
)
