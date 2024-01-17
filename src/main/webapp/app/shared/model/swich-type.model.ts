import { IObyekt } from 'app/shared/model/obyekt.model';

export interface ISwichType {
  id?: number;
  name?: string;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<ISwichType> = {};
