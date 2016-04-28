;----------------------------------------------------------
; Activity: Project: Relational Algebra DSL
; Date: May 4, 2016.
; Author:
;          A01020319 Fernando Gómez Herrera
;----------------------------------------------------------

(ns rel-algebra
  (:require [clojure.string :as str])
  (:use clojure.test))

; UTILS
(defrecord Relation
  [column-names rows])

  ;; Override Java's Object.toString method. This allows using
  ;; the 'str' function with Rectangle instances.
  ;Object
  ;(toString [this]
    ;(declare str-rectangle) ; str-rectangle is declared later.
    ;(str-rectangle this)))

(defn create-record
  "Creates a relation record with attributes [column-names rows]"
  [lst]
  (Relation. (first lst) (rest lst)))
(defn convert
  "Read a list of string values and if converts number to their respective
  integer type."
  [lst]
  (map #(if (re-matches #"\d+" %) (read-string %) %) lst))
(defn split
  "Split by commas"
  [s]
  (str/split s #","))
(defn read-csv
  "Reads a CSV file and splits into lines"
  [filename]
  (let [data (str/split-lines (slurp filename))
        header (first data)
        rows (rest data)]
    (->>
      (map split rows)
      (map convert)
      (cons (split header)))))
(defn widest
  "Get the length of the widest elemenet as str in the list"
  [lst]
  0)

(defn relation
  "This factory function creates a new relation object, taking the data from a table contained in a CSV file. The relation object must be an instance of a record (created with defrecord) or type (created with deftype). The parameter file-name must be a keyword naming a file with a .csv extension contained in the current directory."
  [file-name]
  (->>
    (read-csv (str (name file-name) ".csv"))
    create-record))

(deftest test-convert
  (is
    (= '(1 3 "ok" "ok2" "2ok" "string" 2345)
       (convert '("1" "3" "ok" "ok2" "2ok" "string" "2345")))))
(deftest test-wide
  (is (= 5 (widest '("a" "ab" "abc" "abcd" "abcde")))) ; 5 = abcde
  (is (= 3 (widest '("id" 1 2 12 20 300 22 111)))) ; 3 = 300 or 111
  )

(run-tests)