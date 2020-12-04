(ns day4.core (:gen-class :main true))

(defn -main [& args]
  (def passports (->>
    (->
      (slurp "resources/passports.txt")
      (clojure.string/split #"\n\n")
    )
    (map #(clojure.string/replace % #"\n" " "))
    (map #(clojure.string/split % #" |:"))
    (map #(apply hash-map %))
  ))

  (defn isBetween [value, min, max] (
    -> value
       (and (<= min (read-string value)))
       (and (>= max (read-string value)))
  ))

  (defn isYearBetween [value, min, max] (
    -> value
       (and (re-matches #"\d{4}" value))
       (and (isBetween value min max))
  ))

  (defn isValidHeight [value]
      (def parsedHeight (re-matches #"(\d+)(cm|in)" (or value "")))
      (if (= (last parsedHeight) "cm")
        (isBetween (nth parsedHeight 1) 150 193)
        (isBetween (nth parsedHeight 1) 59 76)
      )
  )

  (defn isValidHairColour [value] (
    -> value
       (and (re-matches #"#[\da-f]{6}" value))
  ))

  (defn isValidEyeColour [value] (
    -> value
       (and (some #(= % value) (list "amb" "blu" "brn" "gry" "grn" "hzl" "oth")))
  ))

  (defn isValidPassportId [value] (
    -> value
       (and (re-matches #"\d{9}" value))
  ))

  (defn isValid [passport] (
    ->
      (isYearBetween (get passport "byr") 1920 2002)
      (and (isYearBetween (get passport "iyr") 2010 2020))
      (and (isYearBetween (get passport "eyr") 2020 2030))
      (and (isValidHeight (get passport "hgt")))
      (and (isValidHairColour (get passport "hcl")))
      (and (isValidEyeColour (get passport "ecl")))
      (and (isValidPassportId (get passport "pid")))
  ))

  (->>
    (filter isValid passports)
    (count)
    (println)
  )
)
