import { IStoykaType } from 'app/shared/model/stoyka-type.model';

export interface IStoyka {
  id?: number;
  name?: string | null;
  info?: string | null;
  stoykaType?: IStoykaType | null;
}

export const defaultValue: Readonly<IStoyka> = {};
