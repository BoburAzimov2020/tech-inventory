import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TerminalServer from './terminal-server';
import TerminalServerDetail from './terminal-server-detail';
import TerminalServerUpdate from './terminal-server-update';
import TerminalServerDeleteDialog from './terminal-server-delete-dialog';

const TerminalServerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TerminalServer />} />
    <Route path="new" element={<TerminalServerUpdate />} />
    <Route path=":id">
      <Route index element={<TerminalServerDetail />} />
      <Route path="edit" element={<TerminalServerUpdate />} />
      <Route path="delete" element={<TerminalServerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TerminalServerRoutes;
