import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Region from './region';
import District from './district';
import ObjectTasnifi from './object-tasnifi';
import ObjectTasnifiTuri from './object-tasnifi-turi';
import Loyiha from './loyiha';
import BuyurtmaRaqam from './buyurtma-raqam';
import Obyekt from './obyekt';
import Attachment from './attachment';
import Shelf from './shelf';
import ShelfType from './shelf-type';
import CameraType from './camera-type';
import CameraBrand from './camera-brand';
import Camera from './camera';
import SwichType from './swich-type';
import Swich from './swich';
import Ups from './ups';
import Rozetka from './rozetka';
import Avtomat from './avtomat';
import CabelType from './cabel-type';
import Cabel from './cabel';
import StoykaType from './stoyka-type';
import Stoyka from './stoyka';
import ProjectorType from './projector-type';
import Projector from './projector';
import SvitaforDetector from './svitafor-detector';
import TerminalServer from './terminal-server';
import Stabilizator from './stabilizator';
import Akumulator from './akumulator';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="region/*" element={<Region />} />
        <Route path="district/*" element={<District />} />
        <Route path="object-tasnifi/*" element={<ObjectTasnifi />} />
        <Route path="object-tasnifi-turi/*" element={<ObjectTasnifiTuri />} />
        <Route path="loyiha/*" element={<Loyiha />} />
        <Route path="buyurtma-raqam/*" element={<BuyurtmaRaqam />} />
        <Route path="obyekt/*" element={<Obyekt />} />
        <Route path="attachment/*" element={<Attachment />} />
        <Route path="shelf/*" element={<Shelf />} />
        <Route path="shelf-type/*" element={<ShelfType />} />
        <Route path="camera-type/*" element={<CameraType />} />
        <Route path="camera-brand/*" element={<CameraBrand />} />
        <Route path="camera/*" element={<Camera />} />
        <Route path="swich-type/*" element={<SwichType />} />
        <Route path="swich/*" element={<Swich />} />
        <Route path="ups/*" element={<Ups />} />
        <Route path="rozetka/*" element={<Rozetka />} />
        <Route path="avtomat/*" element={<Avtomat />} />
        <Route path="cabel-type/*" element={<CabelType />} />
        <Route path="cabel/*" element={<Cabel />} />
        <Route path="stoyka-type/*" element={<StoykaType />} />
        <Route path="stoyka/*" element={<Stoyka />} />
        <Route path="projector-type/*" element={<ProjectorType />} />
        <Route path="projector/*" element={<Projector />} />
        <Route path="svitafor-detector/*" element={<SvitaforDetector />} />
        <Route path="terminal-server/*" element={<TerminalServer />} />
        <Route path="stabilizator/*" element={<Stabilizator />} />
        <Route path="akumulator/*" element={<Akumulator />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
