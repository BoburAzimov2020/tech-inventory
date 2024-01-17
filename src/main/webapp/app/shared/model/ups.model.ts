import { IObyekt } from 'app/shared/model/obyekt.model';

export interface IUps {
  id?: number;
  name?: string | null;
  model?: string | null;
  power?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<IUps> = {};
