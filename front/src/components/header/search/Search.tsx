import React from "react";

import Async from "react-select/async";
import { OptionsType, OptionTypeBase } from "react-select";

import styles from "./Search.module.css";

const Search: React.FC = () => {
  const name = "SEARCH";

  const options: OptionsType<OptionTypeBase> = [
    { value: "country", label: "COUNTRY" },
    { value: "feature", label: "FEATURE" },
    { value: "hotel", label: "HOTEL" },
    { value: "review", label: "REVIEW" },
    { value: "tour", label: "TOUR" },
    { value: "user", label: "USER" },
  ];

  const loadOptions = (
    inputValue: string,
    callback: (options: OptionsType<OptionTypeBase>) => void,
  ) => {
    console.log(inputValue);
    callback(options);
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
        loadOptions={loadOptions}
      />
    </form>
  );
};

export default Search;
