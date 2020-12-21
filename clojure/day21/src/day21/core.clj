(ns day21.core)

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

(defn get-possible-ingredients [menu allergen] (->>
  menu
  (filter (fn [dish] (some #(= allergen %) (:allergens dish))))
  (map :ingredients)
  (map set)
  (reduce clojure.set/intersection)
))

(defn -main
  [& args]
  (println "Hello World"))
