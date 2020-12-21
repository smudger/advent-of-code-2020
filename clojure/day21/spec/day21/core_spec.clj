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

(describe "The ingredients getter"
  (it "should return a unique set of ingredients from the menu"
    (should= #{"abc" "def" "ghi" "jkl" "noj" "awdwa" "adwa"} (get-ingredients [
      {:ingredients ["abc" "def", "abc"], :allergens ["fish" "egg" "nuts"]}
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

(describe "The menu translator"
  (it "should return a map of translations from ingredients to their allergens"
    (should= {"nuts" "def", "egg" "abc", "sesame" "ghi", "soy" "fod"} (translate-menu [
      {:ingredients ["abc" "def" "ghi"], :allergens ["egg"]}
      {:ingredients ["def" "abc"], :allergens ["nuts" "egg"]}
      {:ingredients ["ghi" "def"], :allergens ["sesame" "nuts"]}
      {:ingredients ["def" "abc" "bdh" "dwa"], :allergens ["egg"]}
      {:ingredients ["abc" "def" "fod"], :allergens ["soy"]}
    ]))
  )
  (it "should remove taken allergens as they are assigned"
    (should= {"dairy" "mxmxvkd", "fish" "sqjhc", "soy" "fvjkl"} (translate-menu [
      {:ingredients ["mxmxvkd" "kfcds" "sqjhc" "nhms"], :allergens ["dairy" "fish"]}
      {:ingredients ["trh" "fvjkl" "sbzzf" "mxmxvkd"], :allergens ["dairy"]} 
      {:ingredients ["sqjhc" "fvjkl"], :allergens ["soy"]}
      {:ingredients ["sqjhc" "mxmxvkd" "sbzzf"], :allergens ["fish"]}
    ]))
  )
)

(describe "The safe ingredient finder"
  (it "should find the ingredients in the menu that are not allergenic"
    (should= #{"bdh" "fgi" "dwa" "xyz"} (find-safe-ingredients [
      {:ingredients ["abc" "def" "ghi" "fgi"], :allergens ["egg"]}
      {:ingredients ["def" "abc"], :allergens ["nuts" "egg"]}
      {:ingredients ["ghi" "def"], :allergens ["sesame" "nuts"]}
      {:ingredients ["def" "abc" "bdh" "dwa" "xyz"], :allergens ["egg"]}
      {:ingredients ["abc" "def" "fod"], :allergens ["soy"]}
    ]))
  )
)

(describe "The ingredient counter"
  (it "should count the number of times an ingredient appears in the menu"
    (should= 3 (count-ingredient [
      {:ingredients ["abc" "def" "ghi" "fgi"], :allergens ["egg"]}
      {:ingredients ["def" "abc"], :allergens ["nuts" "egg"]}
      {:ingredients ["ghi" "def"], :allergens ["sesame" "nuts"]}
      {:ingredients ["def" "bdh" "dwa" "xyz"], :allergens ["egg"]}
      {:ingredients ["abc" "def" "fod"], :allergens ["soy"]}
    ] "abc"))
  )
)

(describe "The puzzle solvers"
  (it "should solve puzzle 1"
    (should= 5 (solve-1 "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\ntrh fvjkl sbzzf mxmxvkd (contains dairy)\nsqjhc fvjkl (contains soy)\nsqjhc mxmxvkd sbzzf (contains fish)"))
  )
)
