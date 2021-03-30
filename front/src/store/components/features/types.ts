export enum FeatureActionTypes {
  FEATURES_READ_ALL_FEATURES = "features/readAllFeatures",
}

export interface IFeature {
  id: number;
  name: string;
}

export type IFeatures = [IFeature] | null;
