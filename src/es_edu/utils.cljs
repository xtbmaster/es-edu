(ns es-edu.utils
  (:require
    [dommy.core :as dommy])
  (:require-macros
    [hiccups.core :as hiccups :refer [html]]))

(defn as-seq
  "Convert a set of DOM node to a CLJS sequence"
  [nodes]
  (let [ length (fn [nodes] (. nodes -length))
         item (fn [nodes n] (.item nodes n))]
    (for [i (range (length nodes))]
      (item nodes i))))

(defn by-tag [tag]
  (as-seq
    (.getElementsByTagName js/document (name tag))))

(defn by-class [klass]
  (as-seq
    (.getElementsByClassName js/document klass)))

(defn by-id [id]
  (as-seq
    (.getElementById js/document id)))

(defn set-attribute
  [dom attr value]
  (aset dom attr value))


(defn set-innerHTML
  [dom value]
  (set! (.-innerHTML dom) value))

(defn read-innerHTML
  [dom]
  (.-innerHTML dom))


(defn set-attribute!
  [dom attr value]
  (dommy/set-attr! dom attr value))


(defn remove-attribute!
  [dom attr]
  (dommy/remove-attr! dom attr))


(defn create-element
  [tag id & [nodes]]
  (let [el (dommy/create-element tag)]
    (aset el "id" id)
    (when nodes (dommy/set-html! el (html nodes)))
    el))

(defn component-value
  [component-id]
  (->
    js/document
    (.getElementById component-id)
    (.-value)))

(defn clj->json [ds]
  (.stringify js/JSON (clj->js ds)))

(defn change-back-color []
  ())

(defn set-item!
  "Set `key` in browser's localStorage to `val`."
  [key val]
  (.setItem (.-localStorage js/window) key val))

(defn get-item
  "Returns value of `key` from browser's localStorage."
  [key]
  (.getItem (.-localStorage js/window) key))

(defn remove-item!
  "Remove the browser's localStorage value for the given `key`"
  [key]
  (.removeItem (.-localStorage js/window) key))
