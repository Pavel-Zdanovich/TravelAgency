import React from "react";

import Async from "react-select/async";

import styles from "./Search.module.css";

const Search: React.FC = () => {
  const name = "SEARCH";

  const url = "http://localhost:8080/travelagency/hotels";

  const initRequest: RequestInit = {
    method: "GET",
    headers: [[""]],
  };

  const promiseOptions = (inputValue: string) => {
    initRequest["body"] = inputValue;
    return fetch(url, initRequest);
  };

  return (
    <form className={styles.form}>
      <Async
        className={styles.search}
        classNamePrefix={"search"}
        components={{
          IndicatorsContainer: () => null,
        }}
        name={name}
        placeholder={name}
        loadOptions={promiseOptions}
      />
    </form>
  );
};

export default Search;
