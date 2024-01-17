import { ISwichType } from 'app/shared/model/swich-type.model';

export interface ISwich {
  id?: number;
  name?: string | null;
  model?: string | null;
  portNumber?: string | null;
  info?: string | null;
  swichType?: ISwichType | null;
}

export const defaultValue: Readonly<ISwich> = {};
