(ns es-edu.macros
  (:refer-clojure :exclude [slurp spit]))

(defmacro slurp [file]
  (clojure.core/slurp file))

