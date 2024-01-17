import { IObyekt } from 'app/shared/model/obyekt.model';

export interface ITerminalServer {
  id?: number;
  name?: string | null;
  model?: string | null;
  ip?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<ITerminalServer> = {};
