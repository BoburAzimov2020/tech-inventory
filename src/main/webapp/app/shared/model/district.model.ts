import { IRegion } from 'app/shared/model/region.model';

export interface IDistrict {
  id?: number;
  name?: string;
  info?: string | null;
  region?: IRegion | null;
}

export const defaultValue: Readonly<IDistrict> = {};
