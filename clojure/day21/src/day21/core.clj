(ns day21.core
 (:require [clojure.set])
)

(defn parse-menu [menu] (->>
  (->
    menu
    (clojure.string/replace #",|\(|\)" "")
    (clojure.string/split-lines)
  )
  (map #(clojure.string/split % #" "))
  (map (fn [dish] (map #(into [] %) (partition-by #(= "contains" %) dish))))
  (map #(remove #{'("contains")} %))
  (mapv #(zipmap [:ingredients :allergens] %))
))

(defn get-allergens [menu] (->>
  menu
  (map :allergens)
  (reduce into #{})
))

(defn get-ingredients [menu] (->>
  menu
  (map :ingredients)
  (reduce into #{})
))

(defn get-possible-ingredients [menu allergen] (->>
  menu
  (filter (fn [dish] (some #(= allergen %) (:allergens dish))))
  (map :ingredients)
  (map set)
  (reduce clojure.set/intersection)
))

(defn remove-used [mappings mapping] (let [correct (->>
  mappings
  (vals)
  (reduce into #{})
  (clojure.set/difference (val mapping))
)] (hash-map (key mapping) correct)
))

(defn find-correct
  ([possibilities] (find-correct {} possibilities))
  ([mappings possibilities]
    (if (empty? possibilities)
        mappings
        (let [sorted (->>
          possibilities
          (map #(remove-used mappings %))
          (reduce into {})
          (sort-by #(count (val %)))
        )]
          (recur (assoc mappings (key (first sorted)) (val (first sorted))) (rest sorted))
        )
    )
  )
)

(defn translate-menu [menu] (let [possibilities (->>
  menu
  (get-allergens)
  (mapv #(hash-map % (get-possible-ingredients menu %)))
  (reduce into {})
  )] (->>
      possibilities
      (find-correct)
      (reduce-kv #(assoc %1 %2 (first %3)) {})
  )
))

(defn find-safe-ingredients [menu] (->>
  menu
  (translate-menu)
  (vals)
  (set)
  (clojure.set/difference (get-ingredients menu))
))

(defn count-ingredient [menu ingredient] (let [counts (->>
  menu
  (map :ingredients)
  (reduce into [])
  (frequencies)
)] (get counts ingredient)
))

(defn solve-1 [input] (let [menu (parse-menu input)]
  (->>
    (find-safe-ingredients menu)
    (map #(count-ingredient menu %))
    (reduce +)
  )
))

(defn solve-2 [input] (let [menu (parse-menu input)]
  (->>
    menu
    (translate-menu)
    (sort-by #(key %))
    (vals)
    (clojure.string/join ",")
  )
))

(defn -main
  [& args]
  (println (solve-1 (slurp "resources/menu.txt")))
  (println (solve-2 (slurp "resources/menu.txt")))
)
