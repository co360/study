(ns db-examples.yesql
  (:require [db-examples.core :refer [db]]
            [clojure.java.jdbc :as sql]
            [yesql.core :refer [defquery defqueries]]))

(defquery find-user "find_user.sql")

;(find-user {:id "foo"} {:connection db})

(defquery find-user-with-connection "find_user.sql" {:connection db})

;(find-user {:id "foo"})

(defquery select-date "select_date.sql" {:connection db})

;; (select-date {} {:result-set-fn first
;;                  :row-fn :now
;;                  :identifiers identity})

(defquery add-user! "add_user.sql" {:connection db})

;(add-user! {:id "a-new-user" :pass "foo"})

(defquery add-user<! "add_user.sql" {:connection db})

;(add-user<! {:id "another-user" :pass "foo"})

(defqueries "queries.sql" {:connection db})

(defn find-user-transaction []
  (sql/with-db-transaction [t-conn db]
    {:limeys (find-user {:id "foo"} {:connection t-conn})}
    {:yanks  (find-user {:id "bar"} {:connection t-conn})}))

;(find-user-transaction)
