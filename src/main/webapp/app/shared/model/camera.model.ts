import { ICameraType } from 'app/shared/model/camera-type.model';
import { ICameraBrand } from 'app/shared/model/camera-brand.model';
import { CameraStatus } from 'app/shared/model/enumerations/camera-status.model';

export interface ICamera {
  id?: number;
  name?: string;
  model?: string | null;
  serialNumber?: string | null;
  ip?: string;
  status?: keyof typeof CameraStatus | null;
  info?: string | null;
  cameraType?: ICameraType | null;
  cameraBrand?: ICameraBrand | null;
}

export const defaultValue: Readonly<ICamera> = {};
