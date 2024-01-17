import { ICabelType } from 'app/shared/model/cabel-type.model';

export interface ICabel {
  id?: number;
  name?: string | null;
  model?: string | null;
  info?: string | null;
  cabelType?: ICabelType | null;
}

export const defaultValue: Readonly<ICabel> = {};
