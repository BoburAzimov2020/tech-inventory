import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SvitaforDetector from './svitafor-detector';
import SvitaforDetectorDetail from './svitafor-detector-detail';
import SvitaforDetectorUpdate from './svitafor-detector-update';
import SvitaforDetectorDeleteDialog from './svitafor-detector-delete-dialog';

const SvitaforDetectorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SvitaforDetector />} />
    <Route path="new" element={<SvitaforDetectorUpdate />} />
    <Route path=":id">
      <Route index element={<SvitaforDetectorDetail />} />
      <Route path="edit" element={<SvitaforDetectorUpdate />} />
      <Route path="delete" element={<SvitaforDetectorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SvitaforDetectorRoutes;
