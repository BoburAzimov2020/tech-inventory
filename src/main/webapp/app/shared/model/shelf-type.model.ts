import { IObyekt } from 'app/shared/model/obyekt.model';

export interface IShelfType {
  id?: number;
  name?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<IShelfType> = {};
