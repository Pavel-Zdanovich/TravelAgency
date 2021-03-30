import React from "react";
import Select, { OptionsType, OptionTypeBase, ValueType } from "react-select";

import {
  connector,
  IFeaturesConnectedProps,
} from "../../../store/components/features/actions";
import { IFeatures } from "../../../store/components/features/types";

import styles from "./MultiCheckSelect.module.css";

interface IMultiCheckSelectProps extends IFeaturesConnectedProps {
  name: string;
}

export type { IMultiCheckSelectProps };

const MultiCheckSelect: React.FC<IMultiCheckSelectProps> = ({
  name,
  ...props
}: IMultiCheckSelectProps) => {
  const features: IFeatures = props.features;

  const options: OptionsType<OptionTypeBase> = features
    ? features.map((feature) => {
        return {
          value: feature.name,
          label: feature.name,
        };
      })
    : [];

  const [value, setValue] = React.useState<OptionsType<OptionTypeBase>>();

  const handleChange = (value: ValueType<OptionTypeBase, true>) => {
    setValue(value as OptionsType<OptionTypeBase>);
  };

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
        options={options}
        value={value}
        onChange={handleChange}
        isMulti={true}
        closeMenuOnSelect={false}
        menuIsOpen={true}
        defaultMenuIsOpen={true}
      />
    </>
  );
};

export default connector(MultiCheckSelect);
