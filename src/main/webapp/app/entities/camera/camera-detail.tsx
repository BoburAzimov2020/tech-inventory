import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './camera.reducer';

export const CameraDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cameraEntity = useAppSelector(state => state.camera.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cameraDetailsHeading">Camera</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cameraEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
            <UncontrolledTooltip target="name">Nomi &#39;Camera1&#39;</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
            <UncontrolledTooltip target="model">Model.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.model}</dd>
          <dt>
            <span id="serialNumber">Serial Number</span>
            <UncontrolledTooltip target="serialNumber">Seriya raqami.</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.serialNumber}</dd>
          <dt>
            <span id="ip">Ip</span>
            <UncontrolledTooltip target="ip">IP</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.ip}</dd>
          <dt>
            <span id="status">Status</span>
            <UncontrolledTooltip target="status">Status</UncontrolledTooltip>
          </dt>
          <dd>{cameraEntity.status}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{cameraEntity.info}</dd>
          <dt>Camera Type</dt>
          <dd>{cameraEntity.cameraType ? cameraEntity.cameraType.id : ''}</dd>
          <dt>Camera Brand</dt>
          <dd>{cameraEntity.cameraBrand ? cameraEntity.cameraBrand.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/camera" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/camera/${cameraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CameraDetail;
