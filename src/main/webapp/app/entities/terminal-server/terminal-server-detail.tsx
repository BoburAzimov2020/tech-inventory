import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './terminal-server.reducer';

export const TerminalServerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const terminalServerEntity = useAppSelector(state => state.terminalServer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="terminalServerDetailsHeading">Terminal Server</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{terminalServerEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{terminalServerEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{terminalServerEntity.model}</dd>
          <dt>
            <span id="ip">Ip</span>
          </dt>
          <dd>{terminalServerEntity.ip}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{terminalServerEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{terminalServerEntity.obyekt ? terminalServerEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/terminal-server" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/terminal-server/${terminalServerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TerminalServerDetail;
