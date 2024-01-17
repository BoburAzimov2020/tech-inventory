import { IObyekt } from 'app/shared/model/obyekt.model';

export interface IRozetka {
  id?: number;
  name?: string | null;
  model?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<IRozetka> = {};
