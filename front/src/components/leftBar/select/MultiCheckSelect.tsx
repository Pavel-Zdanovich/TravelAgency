import React from "react";

import Select, { OptionsType, OptionTypeBase, ValueType } from "react-select";

import styles from "./MultiCheckSelect.module.css";

interface IMultiCheckSelect {
  name: string;
}

export type { IMultiCheckSelect };

const MultiCheckSelect: React.FC<IMultiCheckSelect> = ({
  name,
}: IMultiCheckSelect) => {
  const options: OptionsType<OptionTypeBase> = [
    { value: "foo", label: "Foo" },
    { value: "bar", label: "Bar" },
    { value: "bat", label: "Bat" },
  ];

  /*const [multiValue, setMultiValue] = React.useState<OptionsType<OptionTypeBase>>();

  const handleChange = (value: ValueType<OptionTypeBase, true>) => {
    setMultiValue(value as OptionsType<OptionTypeBase>);
  };*/

  return (
    <>
      <Select
        className={styles.select}
        classNamePrefix={"select"}
        components={{
          DropdownIndicator: () => null,
          IndicatorSeparator: () => null,
        }}
        name={name}
        placeholder={name}
        //value={multiValue}
        options={options}
        //onChange={handleChange}
        isMulti={true}
        closeMenuOnSelect={false}
        menuIsOpen={true}
        defaultMenuIsOpen={true}
      />
    </>
  );
};

export default MultiCheckSelect;
