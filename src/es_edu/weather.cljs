(ns es-edu.weather
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [ajax.core :refer [GET POST]]
    [cljs.core.async :refer [chan <! >! timeout]]))

(def weather-handler-chan (chan))

(def ^{:private true} api-key "f3ed121e4c04625fc018cdf881d637f0")

(defn- endpoint [city]
  (str "http://api.openweathermap.org/data/2.5/weather?"
    "units=metric&"
    "q=" city "&"
    "APPID=" api-key))

(defn get-icon [weather]
  (str "http://openweathermap.org/img/w/"
    (get weather "icon") ".png"))

(defn read-weather [weather]
  (first (get-in (js->clj weather) ["weather"])))

(defn- error-handler [{:keys [status status-text]}]
  (prn (str "Something bad has happend: " status " " status-text)))

(defn get-weather [city]
  (prn "Fetching Weather for: " city)
  (GET (endpoint city)
    { :handler #(go (>! weather-handler-chan %))
      :error-handler error-handler}))

(defn store-in-mongo [collection msg]
  (POST (str "http://localhost:27017/local/" collection "/")
    { :body msg
      :handler #(prn "OK")
      :error-handler #(prn "ERROR:" %1 %2)}))





