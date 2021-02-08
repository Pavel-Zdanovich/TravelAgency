import React from "react";

import styles from "./L10n.module.css";
import Select, { OptionsType, OptionTypeBase } from "react-select";

const L10n: React.FC = () => {
  const lang = "LANG";

  const defaultValue: OptionTypeBase = { value: "en", label: "EN" };

  const langOptions: OptionsType<OptionTypeBase> = [
    defaultValue,
    { value: "ru", label: "RU" },
    { value: "by", label: "BY" },
  ];

  return (
    <div className={styles.l10n}>
      <Select
        className={styles.lang}
        classNamePrefix={lang.toLocaleLowerCase()}
        components={{
          IndicatorsContainer: () => null,
        }}
        name={lang}
        //value={multiValue}
        options={langOptions}
        //onChange={handleChange}
        defaultValue={defaultValue}
      />
    </div>
  );
};

export default L10n;
