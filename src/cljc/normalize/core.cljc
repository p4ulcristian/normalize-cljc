(ns normalize.core
  (:require
    [clojure.string :refer [replace lower-case]]
    #?(:cljs ["normalize-diacritics" :refer [normalize normalizeSync]])))

(defn escape-special-characters [url]
  (replace url #"[^a-zA-Z0-9\u00C0-\u017F\{\-| }]" ""))

(defn deaccent [text]
  "Remove accent from string"
  #?(:clj (let [normalized (java.text.Normalizer/normalize text java.text.Normalizer$Form/NFD)]
            (replace normalized #"\p{InCombiningDiacriticalMarks}+" ""))
     :cljs  (normalizeSync text)))

(defn space->separator [text]
  (replace text #"[ |-]{1,}" "-"))

(defn cut-special-char [text]
  (replace text #"[^\w\s]" ""))

(defn normalize-string [text]
  (-> text
    (str)
    (deaccent)
    (cut-special-char)
    (escape-special-characters)
    (space->separator)
    (lower-case)))
