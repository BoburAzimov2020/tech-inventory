import { IObyekt } from 'app/shared/model/obyekt.model';

export interface IStoykaType {
  id?: number;
  name?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<IStoykaType> = {};
