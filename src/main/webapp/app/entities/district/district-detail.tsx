import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './district.reducer';

export const DistrictDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const districtEntity = useAppSelector(state => state.district.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="districtDetailsHeading">District</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{districtEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{districtEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{districtEntity.info}</dd>
          <dt>Region</dt>
          <dd>{districtEntity.region ? districtEntity.region.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/district" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/district/${districtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DistrictDetail;
