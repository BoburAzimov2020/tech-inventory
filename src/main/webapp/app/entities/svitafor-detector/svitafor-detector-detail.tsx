import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './svitafor-detector.reducer';

export const SvitaforDetectorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const svitaforDetectorEntity = useAppSelector(state => state.svitaforDetector.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="svitaforDetectorDetailsHeading">Svitafor Detector</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{svitaforDetectorEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{svitaforDetectorEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{svitaforDetectorEntity.model}</dd>
          <dt>
            <span id="portNumber">Port Number</span>
          </dt>
          <dd>{svitaforDetectorEntity.portNumber}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{svitaforDetectorEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{svitaforDetectorEntity.obyekt ? svitaforDetectorEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/svitafor-detector" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/svitafor-detector/${svitaforDetectorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SvitaforDetectorDetail;
