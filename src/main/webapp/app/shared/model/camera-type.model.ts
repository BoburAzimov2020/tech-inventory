import { IObyekt } from 'app/shared/model/obyekt.model';

export interface ICameraType {
  id?: number;
  name?: string;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<ICameraType> = {};
