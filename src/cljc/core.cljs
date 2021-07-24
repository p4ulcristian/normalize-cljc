(ns normalize.core
  (:require
    ["normalize-diacritics" :refer [normalize normalizeSync]]))

(defn escape-special-characters [url]
  (clojure.string/replace url #"[^a-zA-Z0-9\u00C0-\u017F\ ]" ""))

(defn deaccent [the-string]
  "Remove accent from string"
  #?(:cljs (let [normalized (java.text.Normalizer/normalize the-string java.text.Normalizer$Form/NFD)]
             (replace normalized #"\p{InCombiningDiacriticalMarks}+" ""))
     :clj  (normalizeSync the-string)))

(defn space->separator [string]
  (clojure.string/replace string #"[ |-]{1,}" "-"))

(defn cut-special-char [string]
  (replace string #"[^\w\s-]" ""))

(defn normalize-string [string]
  (-> string
    (str)
    (deaccent)
    (cut-special-char)
    (escape-special-characters)
    (space->separator)
    (clojure.string/lower-case)))
