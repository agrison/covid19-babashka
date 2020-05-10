#!/usr/local/bin/bb
(require '[clojure.java.shell :refer [sh]]
         '[babashka.curl :as curl]
         '[clojure.java.io :as io]
         '[table.core :as t]
         '[clojure.tools.cli :as cli])

(def cli-options
  [["-c" "--country <country>" "The country to fetch COVID details for" :default nil]
   ["-g" "--graph" :default false]])

(defn table [detail]
  (-> (dissoc detail :updated :deathsPerOneMillion :testsPerOneMillion :casesPerOneMillion :countryInfo)
      vector
      (t/table :style :unicode)))

(defn fetch [url]
  (-> (curl/get url)
      :body
      (json/parse-string true)))

(defn graph [d title]
  (let [data (apply str (interpose " " d))
        graph (:out (sh "asciigraph" "-h" "10" "-c" title :in data))]
    (println graph)))

(defn graph-values [m]
  (let [transform (fn [s] (str/replace s #"(\d+)/(\d+)/(\d+)" "$3-$1-$2"))
        mapping (into (sorted-map) (map #(vector % (transform %)) (keys m)))
        new-map (clojure.set/rename-keys m mapping)]
    (vals (into (sorted-map) new-map))))

(defn print-country-graph [c]
  (let [{:keys [:cases :deaths :recovered]} (:timeline (fetch (str "https://corona.lmao.ninja/v2/historical/" c)))]
    (graph (graph-values cases) "Number of cases")
    (graph (graph-values deaths) "Number of deaths")
    (graph (graph-values recovered) "Number of recovered")))

(let [{:keys [:country :graph]} (:options (cli/parse-opts *command-line-args* cli-options))]
  (if (nil? country)
    (table (fetch "https://corona.lmao.ninja/v2/all"))
    (do (table (fetch (str "https://corona.lmao.ninja/v2/countries/" country)))
        (when graph (print-country-graph country)))))
