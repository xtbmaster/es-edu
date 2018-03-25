(ns styles
  (:require [garden.def :refer [defrule defstyles defkeyframes]]
    [garden.stylesheet :refer [rule]]))

(defkeyframes slide
  [:from
    { :left "0px" :top "0px"}]
  [:to
    { :left "300px" :top "300px"}])

(defstyles screen
  (let [ h1 (rule :h1)
         body (rule :body)
         div (rule :div)
         milk (rule :.milk)]
    [ slide
      ( (rule :.slide) {
                         :position "absolute"
                         :left "-100px"
                         :width "100px"
                         :height "100px"
                         :z-index -10
                         :animation "slide 0.5s forwards"
                         :animation-delay "0s"})
      (milk 
        { :height "32px"
          :width "32px"
          :float "left"})

      (h1 { :font-size "14px"
            :line-height 1.5
            :text-align "center"})

      (body { :height 400
              :font-family "Pangolin, cursive"
              :font-size "12px"
              :padding-left 0})

      (div {:text-align "center"})]))


