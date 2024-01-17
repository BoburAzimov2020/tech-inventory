import { IObyekt } from 'app/shared/model/obyekt.model';

export interface ISvitaforDetector {
  id?: number;
  name?: string | null;
  model?: string | null;
  portNumber?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<ISvitaforDetector> = {};
