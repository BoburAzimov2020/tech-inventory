import { IProjectorType } from 'app/shared/model/projector-type.model';

export interface IProjector {
  id?: number;
  name?: string | null;
  model?: string | null;
  info?: string | null;
  projectorType?: IProjectorType | null;
}

export const defaultValue: Readonly<IProjector> = {};
