import { IObyekt } from 'app/shared/model/obyekt.model';

export interface IAvtomat {
  id?: number;
  name?: string | null;
  model?: string | null;
  group?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<IAvtomat> = {};
