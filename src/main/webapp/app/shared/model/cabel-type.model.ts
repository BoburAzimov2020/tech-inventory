import { IObyekt } from 'app/shared/model/obyekt.model';

export interface ICabelType {
  id?: number;
  name?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<ICabelType> = {};
